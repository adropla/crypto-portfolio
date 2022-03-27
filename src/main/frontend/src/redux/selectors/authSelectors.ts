import { RootState } from '../store';

export const selectToken = (state: RootState) => state.authSlice.accessToken;
export const selectUsername = (state: RootState) => state.authSlice.name;
export const selectEmail = (state: RootState) => state.authSlice.email;
export const selectIsAuth = (state: RootState) => state.authSlice.isAuth;
