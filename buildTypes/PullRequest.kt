package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerMaster
import jetbrains.buildServer.configs.kotlin.v2019_2.*

object PullRequest : BuildType({
    templates(AbsoluteId("CsdProjects_PullRequest"))
    name = "Pull Request"

    vcs {
        root(RotorManagerMaster)

        branchFilter = "+:pull-requests/*"
    }
})