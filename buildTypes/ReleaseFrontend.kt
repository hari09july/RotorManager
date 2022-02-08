package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerRelease
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object ReleaseFrontend : BuildType({
    templates(RotorManager.buildTypes.FrontendTemplate)
    name = "Prod frontend"

    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("env.Git_Branch", "${RotorManagerRelease.paramRefs.buildVcsBranch}")
        password("connection-string", "credentialsJSON:76badc8f-ad58-4ea7-bb53-6ed2ccce429d", display = ParameterDisplay.HIDDEN)
        param("environment", "prod")
    }

    vcs {
        root(RotorManagerRelease)
    }
})