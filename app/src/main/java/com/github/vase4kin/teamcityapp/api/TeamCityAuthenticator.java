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

package com.github.vase4kin.teamcityapp.api;

import com.github.vase4kin.teamcityapp.storage.api.UserAccount;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Authenticator
 */
public class TeamCityAuthenticator implements okhttp3.Authenticator {

    /**
     * Count of attempts on 401
     */
    private static final int ATTEMPTS_COUNT = 3;
    private UserAccount mUserAccount;

    public TeamCityAuthenticator(UserAccount userAccount) {
        this.mUserAccount = userAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // by default do three attempts
        if (responseCount(response) >= ATTEMPTS_COUNT) {
            return null; // If we've failed 3 times, give up.
        }
        // Use user credentials
        String credential = Credentials.basic(mUserAccount.getUserName(), mUserAccount.getPasswordAsString());
        return response.request().newBuilder()
                .header(TeamCityService.AUTHORIZATION, credential)
                .build();
    }

    /**
     * Response count
     *
     * @param response - Response
     * @return count of response tries failed by 401
     */
    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
