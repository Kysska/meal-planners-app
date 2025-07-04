pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "meal-planners-app"
include(":app")
include(":data:recipe")
include(":data:product")
include(":data:category")
include(":data:mealtime")
include(":feature:library")
include(":common:ui")
include(":core:utils")
include(":core:database")
include(":core:remote")
include(":feature:weekplan")
include(":feature:search")
include(":feature:shoppinglist")
