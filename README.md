# Pungwe CMS
The current state of popular open source java cms is pretty dire... The most popular being 
licensed under both commercial and proprietry licenses; such as magnolia.

I wanted to create a CMS that I can use as a base for other projects and given recent experience 
with drupal, there is a lot to like about it. You can build complete and functional websites very 
rapidly with less of a learning curve and half the hassle (once you know what you're doing).

Pungwe CMS is heavily influenced by drupal 7 and 8. I want to build something that takes the best
of what drupal has to offer and provide something similar based on java. This doesn't mean that it's
a Drupal clone... This just means it's similar and heavily based on my point of view (which means 
lost of type safety, spring oriented design and of course denormalised data, without JCR).

## Modules / Themes
For the time being, it has been decided that we will not support a plugin system like OSGI, due to how it complicates lives
for the developers... It is for this reason that modules have to be included on the classpath as part of your project builds.
When time permits we will look into alternative methods of dynamically loading these...

### Modules
Modules are loaded into their own shared context, this allows for restarting of the relevant application context to turn 
modules on and off. There is no dependency management or plugin system as such. Modules are added to the gradle build file as
runtime dependencies and used for a build:

```groovy
dependencies {
	runtime 'org.module.group:module-name:1.0.0'
}
```

#### Module Definition
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

### Themes
As with modules, themes work in a very similar fashion with one major difference. Themes are hierarchical and as such run in their
own application contexts; with the parent theme's context being the parent application context for that theme. This is to guarantee
hook and bean isolation between completely different themes.

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

#### Status
The theme system is almost complete... Themes can be created using the @Theme annotation, hooks work and the relevant template overrides will work
as well.

## Things to do
- Should probably start populating the wiki with documentation
- Design how Rest API will work with everything (pretty much, should run headless if need be)
- Rest API should override views, so that developers can use pure client side apps
- Get swagger on the go for rest api documentation
- Need to finish designing how themes and templates will work
- Look into app packaging, so that desktop class apps can be built and run locally
- Change / revision system for entities
- Modules should use @ComponentScan or @Bean to create beans.

More to come...