package RotorManager.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object RotorManagerAutoTestMerge : GitVcsRoot({
    name = "Rotor Manager Scheduled Merge"
    url = "https://bitbucket.durr.com/scm/dig/rotor-manager.git"
    branch = "refs/heads/master"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(test)
    """.trimIndent()
    userNameStyle = GitVcsRoot.UserNameStyle.EMAIL
    authMethod = password {
        userName = "jira-service-user"
        password = "zxxb985be016febd48659fa2f2af42debba6f08878274237478"
    }
})

