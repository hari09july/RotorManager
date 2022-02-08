package RotorManager.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object RotorManagerRelease : GitVcsRoot({
    name = "Rotor Manager Release"
    url = "https://bitbucket.durr.com/scm/dig/rotor-manager.git"
    branch = "refs/heads/release"
    authMethod = password {
        userName = "jira-service-user"
        password = "zxxb985be016febd48659fa2f2af42debba6f08878274237478"
    }
})