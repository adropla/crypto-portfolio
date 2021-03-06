import { combineReducers, configureStore } from '@reduxjs/toolkit';
import {
    FLUSH,
    PAUSE,
    PERSIST,
    persistReducer,
    persistStore,
    PURGE,
    REGISTER,
    REHYDRATE,
} from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { cryptoApi } from '../services/coinGekoApi';
import watchListReducer from './reducers/watchListSlice';
import portfoliosReducer from './reducers/portfolioSlice';
import { ethereumApi } from '../services/ethereumApi';
import modalSelectedCoinsReducer from './reducers/modalSelectedCoinsSlice';
import watchListViewReducer from './reducers/watchListViewSlice';
import authSlice from './reducers/authSlice';
import { serverApi } from '../services/serverApi';
import loginModalSlice from './reducers/loginModalSlice';

const persistConfig = {
    key: 'root',
    storage,
    blacklist: [
        'cryptoApi',
        'ethereumApi',
        'newsApi',
        'modalSelectedCoinsReducer',
    ],
};

const rootReducer = combineReducers({
    [cryptoApi.reducerPath]: cryptoApi.reducer,
    watchListReducer,
    modalSelectedCoinsReducer,
    watchListViewReducer,
    [ethereumApi.reducerPath]: ethereumApi.reducer,
    [serverApi.reducerPath]: serverApi.reducer,
    portfolios: portfoliosReducer,
    authSlice,
    loginModalSlice,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

const setupStore = () =>
    configureStore({
        reducer: persistedReducer,
        middleware: (getDefaultMiddleware) =>
            getDefaultMiddleware({
                serializableCheck: {
                    ignoredActions: [
                        FLUSH,
                        REHYDRATE,
                        PAUSE,
                        PERSIST,
                        PURGE,
                        REGISTER,
                    ],
                },
            }).concat(
                cryptoApi.middleware,
                ethereumApi.middleware,
                serverApi.middleware,
            ),
    });

export const store = setupStore();

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof rootReducer>;
export type AppStore = ReturnType<typeof setupStore>;
export type AppDispatch = AppStore['dispatch'];
