package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerTest
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object TestFrontend : BuildType({
    templates(RotorManager.buildTypes.FrontendTemplate)
    name = "Test frontend"

    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("env.Git_Branch", "${RotorManagerTest.paramRefs.buildVcsBranch}")
        password("connection-string", "credentialsJSON:d686d054-5aca-4ec7-a244-c702f1e51218", display = ParameterDisplay.HIDDEN)
        param("environment", "test")
    }

    vcs {
        root(RotorManagerTest)
    }
})