package RotorManager.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Template
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object BackendTemplate : Template({
    name = "Backend Template"

    steps {
        dotnetTest {
            name = "Backend test"
            projects = "test/rm-service/**/RotorManager*/RotorManager*.csproj"
            configuration = "Release"
            outputDir = "artifacts/test-results"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
                assemblyFilters = """
                    +:RotorManager*
                    -:RotorManager*.Test
                    -:RotorManager*.Integration
                    -:BouncyCastle.Crypto
                    -:FakeItEasy
                """.trimIndent()
            }
        }
        powerShell {
            name = "Backend publish"
            formatStderrAsError = true
            scriptMode = file {
                path = "tools/build.ps1"
            }
            param("jetbrains_powershell_scriptArguments", "-target BE-Publish")
        }
        dockerCommand {
            name = "Docker build"
            commandType = build {
                source = file {
                    path = "build/rm-service/Dockerfile"
                }
                contextDir = ".build/rm-service/"
                namesAndTags = """
                        csd/rm-service
                        %acr_server%/csd/rm-service
                    """.trimIndent()
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            name = "Docker login to Azure"
            commandType = other {
                subCommand = "login"
                commandArgs = "%acr_server% -u %acr_user% -p %acr_pwd%"
            }
        }
        dockerCommand {
            name = "Docker push to Azure"
            commandType = push {
                namesAndTags = "%acr_server%/csd/rm-service:latest"
                removeImageAfterPush = false
            }
        }
        powerShell {
            name = "Restart Azure backend service"
            scriptMode = script {
                content = """
                    az login --service-principal -u %client_id% "-p=%client_secret%" --tenant %tenant_id%
                    az container restart --resource-group %rg_name% --name %service_name%
                    az logout
                """.trimIndent()
            }
        }
    }

    triggers {
        vcs {   
            triggerRules = """+:\projects\rm-service\**"""
        }
    }

})