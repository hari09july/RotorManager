package RotorManager.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Template
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

object FrontendTemplate : Template({
    name = "Frontend Template"

    steps {
        powerShell {
            name = "Test"
            workingDir = "projects/rotormanager"
            scriptMode = file {
                path = "tools/build.ps1"
            }
            param("jetbrains_powershell_scriptArguments", "-target UI-Test")
        }

        powerShell {
            name = "Build Azure frontend"
            scriptMode = file {
                path = "tools/build.ps1"
            }
            param("jetbrains_powershell_scriptArguments", "-target UI-Pack -environment %environment%")
        }

        powerShell {
            name = "Deploy to Azure"
            workingDir = "artifacts/rotormanager"
            scriptMode = script {
                content = """az storage blob upload-batch -s . -d '${'$'}web'  --connection-string "%connection-string%""""
            }
        }
    }

    triggers {
        vcs {
            triggerRules = """+:\projects\rotormanager\**"""
            branchFilter = "+:<default>"
        }
    }

    failureConditions {
        check(errorMessage == false) {
            "Unexpected option value: errorMessage = $errorMessage"
        }
        errorMessage = true
    }
})
