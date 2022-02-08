package RotorManager.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object RotorManagerMaster : GitVcsRoot({
    name = "Rotor Manager Master"
    url = "https://bitbucket.durr.com/scm/dig/rotor-manager.git"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "jira-service-user"
        password = "zxxb985be016febd48659fa2f2af42debba6f08878274237478"
    }
})