package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerMaster
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object DevBackend : BuildType({
    templates(RotorManager.buildTypes.BackendTemplate)
    name = "Dev backend"

    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("env.Git_Branch", "${RotorManagerMaster.paramRefs.buildVcsBranch}")
        param("environment", "dev")
        param("rg_name", "so-%environment%")
        param("service_name", "so-rm-%environment%")
        param("acr_user", "soContainerReg%environment%")
        param("acr_server", "socontainerreg%environment%.azurecr.io")
        password("tenant_id", "credentialsJSON:c2900173-0fe9-404c-81ac-ecc84e78101c", display = ParameterDisplay.HIDDEN) // ID of primary Azure tenant
        password("client_id", "credentialsJSON:d013e927-1bc3-4710-acfe-1c8bdb604a8d", display = ParameterDisplay.HIDDEN) // service principal with contributor access to infra resource group
        password("client_secret", "credentialsJSON:72fee424-9e53-4172-941a-f1be04729a1e", display = ParameterDisplay.HIDDEN) // service principal with contributor access to infra resource group
        password("acr_pwd", "credentialsJSON:ce88df60-f89a-4b61-873b-9d7c98f1b760", display = ParameterDisplay.HIDDEN)
    }

    vcs {
        root(RotorManagerMaster)
    }
})