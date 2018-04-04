/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.login.view;

/**
 * Receiving callback from {@link LoginViewImpl} to {@link com.github.vase4kin.teamcityapp.login.presenter.LoginPresenterImpl}
 */
public interface OnLoginButtonClickListener {

    /**
     * Handle on login button click for user creation
     *
     * @param serverUrl - TeamCity server url
     * @param userName - User name
     * @param password - User password
     */
    void onUserLoginButtonClick(String serverUrl, String userName, String password, boolean isSslDisabled);

    /**
     * Handle on login button click for guest user creation
     *
     * @param serverUrl - TeamCity server url
     */
    void onGuestUserLoginButtonClick(String serverUrl, boolean isSslDisabled);

    /**
     * On ignore ssl switch click
     */
    void onDisableSslSwitchClick();
}
