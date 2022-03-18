/* eslint-disable no-param-reassign */
import { createSlice } from '@reduxjs/toolkit';

type IAuth = {
    token: string;
};

const initialState: IAuth = {
    token: '',
};

const authSlice = createSlice({
    name: 'authSlice',
    initialState,
    reducers: {},
});

export default authSlice.reducer;
