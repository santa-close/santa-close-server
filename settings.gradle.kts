rootProject.name = "santa-close-server"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include(":server-lib")
include(":server-app")
