/* eslint-disable no-param-reassign */
import { createSlice } from '@reduxjs/toolkit';

type Modal = {
    visible: boolean;
};

const initialState: Modal = {
    visible: false,
};

const loginModalSlice = createSlice({
    name: 'loginModalSlice',
    initialState,
    reducers: {
        toogleLoginModalVisible: (state) => {
            state.visible = !state.visible;
        },
    },
});

export const { toogleLoginModalVisible } = loginModalSlice.actions;
export default loginModalSlice.reducer;
