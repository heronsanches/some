package hsgpf.some.utility

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

fun setupDeviceConfigurations() {
   val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
   disableDeviceAnimations(uiDevice)
   defineDeviceLongClick(uiDevice)
   disableVirtualDeviceKeyboard(uiDevice)
}

/* INFO: It seems that does not take imediate effect on devices with API 23, even if the
   "shell settings list global propertyName" says that it was set.*/
private fun disableDeviceAnimations(uiDevice: UiDevice) {
   uiDevice.executeShellCommand("settings put global window_animation_scale 0.0")
   uiDevice.executeShellCommand("settings put global transition_animation_scale 0.0")
   uiDevice.executeShellCommand("settings put global animator_duration_scale 0.0")
}

private fun defineDeviceLongClick(uiDevice: UiDevice) {
   uiDevice.executeShellCommand("settings put secure long_press_timeout 1500")
}

private fun disableVirtualDeviceKeyboard(uiDevice: UiDevice) {
   uiDevice.executeShellCommand("settings put secure show_ime_with_hard_keyboard 0")
}