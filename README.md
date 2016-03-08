# Pungwe CMS
The current state of popular open source java cms is pretty dire... The most popular being licensed under both commercial and proprietry licenses; such as magnolia.

I wanted to create a CMS that I can use as a base for other projects and given recent experience with drupal, there is a lot to like about it. You can build complete and functional websites very rapidly with less of a learning curve and half the hassle (once you know what you're doing).

Pungwe CMS is heavily influenced by drupal 7 and 8. I want to build something that takes the best of what drupal has to offer and provide something similar based on java. This doesn't mean that it's a Drupal clone... This just means it's similar and heavily based on my point of view (which means lost of type safety, spring oriented design and of course denormalised data, without JCR).

##Modules / Themes
For the time being, it has been decided that we will not support a plugin system like OSGI, due to how it complicates lives
for the developers... It is for this reason that modules have to be included on the classpath as part of your project builds.
When time permits we will look into alternative methods of dynamically loading these...

###Modules
Modules are loaded into their own shared context, this allows for restarting of the relevant application context to turn modules on and off. There is no dependency management or plugin system as such. Modules are added to the gradle build file as
runtime dependencies and used for a build:

```groovy
dependencies {
	runtime 'org.module.group:module-name:1.0.0'
}
```

####Module Definition
Modules are defined using @Module annotation. This should feel like creating standard spring @Configuration classes.

```java
@Module(
	name="mymodule",
	label="My Module",
	description="My Custom Module"
)
@ComponentScan("com.example.mymodule.components")
public class MyModule {
	...
}
```

###Themes
As with modules, themes work in a very similar fashion with one major difference. Themes are hierarchical and as such run in their own application contexts; with the parent theme's context being the parent application context for that theme. This is to guarantee hook and bean isolation between completely different themes.

Themes are added as runtime dependencies to the project build in the same way as modules:

```groovy
dependencies {
	runtime 'org.theme.group:theme-name:1.0.0'
}
```

#### Theme Definition
Themes are defined using the @Theme annotation. This should feel like creating standard spring @Configuration classes.

```java
@Theme(
	name="mytheme", 
	label="My Theme", 
	description="A basic theme definition", 
	regions={"header", "content", "sidebar", "footer"}
)
@ComponentScan("com.example.mytheme.components")
public class MyTheme {

	@Hook("theme")
	public Map<String, String> themeHook() {
		HashMap<String, String> theme = new HashMap<>();
		theme.put("my_view", "my_theme/my_view");
		return theme;
	}
}
```

####Status
The theme system is almost complete... Themes can be created using the @Theme annotation, hooks work and the relevant template overrides will work as well.

##Components & Hooks

###Block System
Blocks are used to render content within regions on a page. These are usually placed by the BlockPageBuilder class, into regions determined by the current default theme.

Blocks are declared using the @Block annotation and are / should be singleton scoped beans in spring terms. They are fairly easy to declare and are instantiated at system startup.

There are a number of default system blocks that can be used for content layout:
- BreadcrumbBlock
- MainContentBlock
- PageTitleBlock
- PrimaryMenuBlock
- SecondaryMenuBlock
- StatusMessageBlock

Each of these system blocks will use convention to display the most appropriate content. The MainContentBlock for instance will by default take output from a controller request mapping and place it in a specific region (however, if the block is not placed and the active theme has the "content" region, content will be placed there regardless).

Blocks are defined by their annotation and are expected to implement the BlockDefinition interface, otherwise they will simply act as standard beans and will not be usable as blocks.

```java
@Block(value = "hello_world_block", label = "Main Content Block", category = "Default")
public class HelloWorldBlock implements BlockDefinition {

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, 
		Map<String, Object> variables) {
		
		// Block element configuration goes here
		elements.add(new PlainTextElement("Hello World");
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// The hello world block has no settings...
	}

}
```

Blocks are configured through the "Structure" admin page, under block layout.

###Hooks
Hooks are fairly similar to events, in that they are executed by name. Hook implementations are declared using the @Hook annotation.

```java
@Component
public class MyComponentWithAHook {

	@Hook("form_alter")
	public void hookFormAlter(FormElement formElement, Form form, FormState state) {
		// implementation here
	}
	
}
```

Hooks are fairly flexible as well. For example; you can define a hook with as many parameters as the hook being called can pass, or you can specify less parameters. The HookService will try to determine what parameters to pass in.

Hooks with no parameters will usually be expected to return a value using the HookCallback interface.

```java
hookService.executeHook("my_hook", new HookCallback() {
	public void call(Class<?> hookClass, Object arg) {
		// Do some processing here
	}
});
```

You can also use java 8 lambdas (I encourage the use of these unreservedly)!

```java
hookService.executeHook("my_hook", (c, o) -> {
	// Do something, this is exactly the same as anonymous implementation of the HookCallback interface!
});
```

Or without a block

```java
hookService.executeHook("my_hook", (c, o) -> someVariable::method);
```

### Entities
You can also create and define entities... Entities are the only true database agnostic way of storing data within the CMS and should be used for any custom modules.

Definition is straight forward in the spirit of this CMS and there are a number of ways you can do this. The preferred method is to define your entity in a custom module with default fields, leaving the definition of your entity types to the admin interface (It should be noted that an admin interface for your entity definition is optional).

To define your entity there are a number of classes you need to use.

The first being the Entity Type itself using the EntityType interface (This needs a lot of work and most likely needs a stereotype):

```java

@Component
public class MyEntityType implements EntityType {
	
	@Override
	public String getType() {
		return "my_entity";
	}
	
	@Override
	public List<FieldConfig> getBaseFields() {
		List<FieldConfig> fields = new LinkedList<>();
		
		// Title
		FieldConfig title = new FieldConfig();
		title.setName("title");
		title.setLabel("Title");
		title.setWeight(-100);  // ensure it's at the top
		title.setRequired(true);
		title.setCardinality(1);
		title.setWidget("string_textfield");
		title.setFormatter("string");
		title.setFieldType("string");
		title.addSetting("size", 200);
		
		fields.add(title);
		
		// Body
		FieldConfig body = new FieldConfig();
		body.setName("body");
		body.setLabel("Body");
		body.setWeight(0);
		body.setLabel("Body");
		body.setCardinality(1);
		body.setWidget("textarea_and_summary");
		body.setFormatter("text_default");
		body.setFieldType("text");
		body.addSetting("rows", 10);
		
		fields.add(body);
		
		return fields;
	}
	
	@Override
	public void buildSettingsForm(List<RenderedElements> elements, Form form, FormState state) {
		// Do something here
	}
}
```

The second part is to create an admin controller for your new entity type.

```java
@Controller
@RequestMapping("/admin/structure/my-entity-types")
public class MyEntityTypeController {

	@Autowired
	private EntityDefinitionService entityDefinitionService;
	
	@MenuItem(
		name = "my-entity-types",
		parent = "system.admin.structure",
		title = "Manage My Entity Types",
		description = "Manage your entity types"
	)
	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> list(Model model, @Request) {
		return () -> {
		
			// Use the default entity definition service, or define your own to wrap it...
		    Page<EntityDefinition> entities = entityDefinitionService.list("my_entity", new PageRequest(pageNumber, maxRows));
		    
		    final TableElement table = new TableElement();
		    table.addHeaderRow(
			    new TableElement.Header("Title"),
			    new TableElement.Header("Description"),
			    new TableElement.Header("Operations")
		    );
		    
		    entities.getContent().stream().forEach(entity -> {
			    AnchorElement entityEditLink = new AnchorElement(
			        entity.getTitle(),
			        "/admin/structure/my-entity-types/edit/" + entity.getId().getBundle(),
			        new PlainTextElement(entity.getTitle())
			    );
			    table.addRow(
				    new TableElement.Column(entityEditLink),
				    new TableElement.Column(new PlainTextElement(entity.getDescription()),
				    new TableElement.Column(new PlainTextElement("")); // add some operations here...
			    );
		    });
		    
		    model.addAttribute("title", "My Entity Types");
		    model.addAttribute("actions", new AnchorElement("Add a new My Entity Type",
			    "/admin/structure/my-entity-types/add",
			    new PlainTextElement("Add a new Entity")
		    ));
		    
		    model.addAttribute("content", table);
		    
		    return "my_entity_type/my_entity_type_list";
		}
	}
	
	...
}
```

Finally if you're using the @ComponentScan annotation on your module, then you will need to ensure that the entity type class you define is in a package that gets scanned. You can alternatively define your entity by declaring it as a bean:

```java

@Module("my_entity_type_module")
public class MyEntityTypeModule {

	@Bean
	public MyEntityType myEntityType() {
		return new MyEntityType();
	}
	
}

```

Entity types are singleton scoped beans. These beans are used to help create entity definition bundles when you save a new entity bundle. If you do not want to manage your entity type using an admin interface, then you can simply use @Hook("install") or @Hook("update_&lt;revision&gt;") to define your entity bundles programatically:

```java

@Module("my_entity_type_module")
public class MyEntityTypeModule {

	@Autowired
	private EntityDefinitionService entityDefinitionService;
	
	@Bean
	public MyEntityType myEntityType() {
		return new MyEntityType();
	}
	
	// This can be defined as many times as you want in your module!
	@Hook("install")
	public void installMyEntityBundle() {
	
		EntityDefinition myBundle = entityDefinitionService.newInstance(myEntityType(), "my_bundle");
		myBundle.setTitle("My Bundle");
		myBundle.setDescription("This is a code implementation of my entity type");
		
		FieldConfig textField = new FieldConfig();
		textField.setName("textfield_field");
		textField.setLabel("Text Field");
		textField.setWeight(1); // under the base fields!
		textField.setRequired(false);
		textField.setCardinality(1);
		textField.setWidget("string_textfield");
		textField.setFormatter("string");
		textField.setFieldType("string");
		textField.addSetting("size", 60);

		myBundle.addField(textField);
		
		// Save the definition to the entity definition store.
		entityDefinitionService.create(myBundle);
	}
	
}


```

#####Note
Entity definitions are stored in the database and at present there is no plan to make that optional. I'm not a big fan of storing lots of config data in the database, but in order to be editable via the admin interface, this is the best place for it right now.


##Things to do
- Should probably start populating the wiki with documentation
- Design how Rest API will work with everything (pretty much, should run headless if need be)
- Rest API should override views, so that developers can use pure client side apps
- Get swagger on the go for rest api documentation
- Need to finish designing how themes and templates will work
- Look into app packaging, so that desktop class apps can be built and run locally
- Finish entity type definition system
- Change / revision system for entities

More to come...