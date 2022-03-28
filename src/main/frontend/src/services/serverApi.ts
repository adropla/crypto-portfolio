import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type { RootState } from '../redux/store';

export interface IUser {
    accessToken: string | null;
    email: string | null;
    name: string | null;
    isAuth: boolean;
}
export interface LoginRequest {
    email: string;
    password: string;
}

const baseUrl = 'https://best-crypto-portfolio.herokuapp.com/api/v1/';

const baseServerQuery = fetchBaseQuery({
    baseUrl,
    prepareHeaders: (headers, { getState }) => {
        const { accessToken } = (getState() as RootState).authSlice;

        if (accessToken) {
            headers.set('authorization', `Bearer ${accessToken}`);
        }

        return headers;
    },
});

export const serverApi = createApi({
    baseQuery: baseServerQuery,
    endpoints: (build) => ({
        login: build.mutation<IUser, LoginRequest>({
            query: (body) => ({
                url: 'auth/authenticate',
                method: 'post',
                body,
            }),
        }),
        signup: build.mutation({
            query: (body) => ({
                url: 'auth/registration',
                method: 'post',
                body,
            }),
        }),
        forgot: build.mutation({
            query: (body) => ({
                url: 'user/update',
                method: 'post',
                body,
            }),
        }),
        refresh: build.mutation({
            query: (body) => ({
                url: 'auth/refresh',
                method: 'post',
                body,
            }),
        }),
    }),
});

export const { useLoginMutation, useSignupMutation, useRefreshMutation } =
    serverApi;
