import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type { RootState } from '../redux/store';

const baseUrl = 'https://best-crypto-portfolio.herokuapp.com/api/v1/';

const baseServerQuery = fetchBaseQuery({
    baseUrl,
    prepareHeaders: (headers, { getState }) => {
        const { token } = (getState() as RootState).authSlice;

        if (token) {
            headers.set('authorization', `Bearer ${token}`);
        }
        headers.set('content-type', 'application/json; charset=UTF-8');

        return headers;
    },
});

export const serverApi = createApi({
    baseQuery: baseServerQuery,
    endpoints: (build) => ({
        login: build.mutation({
            query: (body: { email: string; password: string }) => ({
                url: 'authenticate',
                mode: 'no-cors',
                method: 'post',
                body,
            }),
        }),
    }),
});

export const { useLoginMutation } = serverApi;
