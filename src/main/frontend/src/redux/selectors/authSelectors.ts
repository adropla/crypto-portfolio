import { RootState } from '../store';

export const selectToken = (state: RootState) => state.authSlice.accessToken;
export const selectUsername = (state: RootState) => state.authSlice.username;
export const selectEmail = (state: RootState) => state.authSlice.email;
