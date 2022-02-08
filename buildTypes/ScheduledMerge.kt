package RotorManager.buildTypes

import RotorManager.vcsRoots.RotorManagerAutoTestMerge
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.merge
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

object ScheduledMerge : BuildType({
    name = "Scheduled Merge"

    vcs {
        root(RotorManagerAutoTestMerge)
    }

    triggers {
        schedule {
            schedulingPolicy = weekly {
                dayOfWeek = ScheduleTrigger.DAY.Monday
                hour = 2
            }
            branchFilter = ""
            triggerBuild = always()
        }
    }

    features {
        merge {
            branchFilter = "+:master"
            destinationBranch = "test"
            commitMessage = "Scheduled merge from 'master' to 'test'"
        }
    }
})

