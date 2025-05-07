# Custom Recipes

A Minecraft plugin for creating your own recipes. 

It allows you to:
* Create recipes that will be shown in crafting table 
* Make your recipe automatically discoverable, or discover it manualy when you will want to
* Edit recipes (just through config files for now)

## Instalation

### 1. Get plugin

You can find already builded plugin in [releases](https://github.com/FOFOLA1/CustomCraftingRecipesPlugin/releases). You can also build it on your own if needed.

<details><summary><b>Build</b></summary>

#### Requirements

* Java 21 or newer
* Maven
* Git

#### Build

```sh
git clone https://github.com/FOFOLA1/CustomCraftingRecipesPlugin.git
cd CustomCraftingRecipesPlugin/
mvn package
```

</details>

### 2. Move plugin on the server

If you have chosen to download already built plugin, then you don't have to find anything. But if you built it on your own, first you need to find file `CustomCraftingRecipesPlugin-<version>.jar`, it should be inside `CustomCraftingRecipesPlugin/target`.

Now you need to move this file to `/plugins` folder that is inside your server's root folder.

### 3. Start server

Now just start (or restart) your server and plugin should be ready to go. Of course check logs if everything ran fine.


## Creating recipes

You have here 2 options. Make it inside game through GUI menu or by creating `.yml` config file. I recommend using first method because that handles everything for you.

### From in-game GUI menu

#### 1. Use command `/crecipes` to open menu.
#### 2. Select crafting table and fill out shape of your recipe with output item, you can also choose if you want to make it researchable automaticaly or not.
#### 3. If everything is entered correctly, you should have green wool in top right corner, click on it.
#### 4. Now it will ask you to name your recipe, this name will correspond to config filename located in `/plugins/CustomRecipes/Recipes/CraftingTable/`. you can also enter path, for example `foo/bar` where `foo` is folder and bar will be config file `bar.yml`.

### Creating config file

#### 1. Create new file somewhere in `plugins/CustomRecipes/Recipes/CraftingTable`, it could be even in some folder inside it. I would recommend to use unique file names.
#### 2. Enter data to file, it should look like this:
```yaml
A: WOODEN_PLANKS
shape:
  - AA
  - ' A'
  - AA
output:
  ==: org.bukkit.inventory.ItemStack
  v: 3839
  type: OAK_DOOR
  amount: 2
auto_discover: true
```
First you set up aliases for materials, use only characters A-I. Then you fill it into shape, here are some rules you should know when making the shape:

* Every line represents one line in crafting table, so it has to be from 1 to 3 lines, if your recipe fits into, for example, 2x2 grid, then I reccomend to write it down in just 2 lines so you will be able to craft recipe everywhere in 3x3 grid.

* If there is some blank spot, you should use `''` on that line to be space properly recognised

Then there is output parameter:
* First line just copy, it is here because plugin is using serializer that comes with Java, maybe some day I change it.
* Second parameter `v` represents version of item that should be somehow linked to Minecraft version, there is not much info about it, so just use `3839` value for version `1.20.6` 
* `type` parameter is required, this represents which material will your item have
* `amout` is optional, but if you will not use it, you will everytime get just one of this item.

Now there is just `auto_discover`, and if it is `true`, it will be discovered by players automaticaly.

#### 3. Save it and restart the plugin, it should be ready to go now.