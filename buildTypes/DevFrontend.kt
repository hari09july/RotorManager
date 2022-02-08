package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerMaster
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object DevFrontend : BuildType({
    templates(RotorManager.buildTypes.FrontendTemplate)
    name = "Dev frontend"

    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("env.Git_Branch", "${RotorManagerMaster.paramRefs.buildVcsBranch}")
        password("connection-string", "credentialsJSON:a6cb4627-4b89-4073-bee9-4552c29e3df4", display = ParameterDisplay.HIDDEN)
        param("environment", "dev")
    }

    vcs {
        root(RotorManagerMaster)
    }
})