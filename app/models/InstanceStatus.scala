package models

object InstanceStatus {
  val Deployed = 1
  val Setup = 2
  val Running = 4
}

class InstanceStatus(val bits: Integer) {
  def isFlagSet(flag: Integer) = (bits & flag) != 0

  def isDeployed = isFlagSet(InstanceStatus.Deployed)

  def isSetup = isFlagSet(InstanceStatus.Setup)

  def isRunning = isFlagSet(InstanceStatus.Running)
}