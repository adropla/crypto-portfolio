/* eslint-disable no-param-reassign */
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { IUser } from '../../services/serverApi';

const initialState: IUser = {
    accessToken: null,
    name: null,
    email: null,
    isAuth: false,
};

const authSlice = createSlice({
    name: 'authSlice',
    initialState,
    reducers: {
        setCredentials: (
            state,
            { payload: { accessToken, name, email } }: PayloadAction<IUser>,
        ) => {
            state.accessToken = accessToken;
            state.name = name;
            state.email = email;
            state.isAuth = true;
        },
        clearCredentials: (state) => {
            state.accessToken = null;
            state.name = null;
            state.email = null;
            state.isAuth = false;
        },
    },
});

export const { setCredentials, clearCredentials } = authSlice.actions;
export default authSlice.reducer;
