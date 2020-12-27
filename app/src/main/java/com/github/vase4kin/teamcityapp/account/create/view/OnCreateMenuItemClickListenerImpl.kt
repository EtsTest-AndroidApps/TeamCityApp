/*
 * Copyright 2020 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.account.create.view

import android.view.View
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial

class OnCreateMenuItemClickListenerImpl(
    private val onValidateListener: OnValidateListener,
    private val serverUrl: TextView,
    private val userName: TextView,
    private val password: TextView,
    private val guestUserSwitch: SwitchMaterial,
    private val disableSslSwitch: SwitchMaterial
) : View.OnClickListener {

    override fun onClick(v: View?) {
        if (guestUserSwitch.isChecked) {
            onValidateListener.validateGuestUserData(
                serverUrl.text.toString().trim(),
                disableSslSwitch.isChecked
            )
        } else {
            onValidateListener.validateUserData(
                serverUrl.text.toString().trim(),
                userName.text.toString().trim(),
                password.text.toString().trim(),
                disableSslSwitch.isChecked
            )
        }
    }
}
