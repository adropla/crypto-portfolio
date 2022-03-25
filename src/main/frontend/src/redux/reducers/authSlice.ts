/* eslint-disable no-param-reassign */
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { IUser } from '../../services/serverApi';

const initialState: IUser = {
    accessToken: null,
    username: null,
    email: null,
};

const authSlice = createSlice({
    name: 'authSlice',
    initialState,
    reducers: {
        setCredentials: (
            state,
            { payload: { accessToken, username, email } }: PayloadAction<IUser>,
        ) => {
            state.accessToken = accessToken;
            state.username = username;
            state.email = email;
        },
        clearCredentials: (state) => {
            state.accessToken = null;
            state.username = null;
            state.email = null;
        },
    },
});

export const { setCredentials, clearCredentials } = authSlice.actions;
export default authSlice.reducer;
