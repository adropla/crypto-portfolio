/* eslint-disable no-unused-vars */
import { ActionCreatorWithoutPayload } from '@reduxjs/toolkit';
import { useState } from 'react';
import { Modal } from '../redux/reducers/loginModalSlice';
import { useAppDispatch } from './redux';

type useModalVisibleProps = (
    init: boolean,
    toogleModalStore?: ActionCreatorWithoutPayload<string>,
) => any;

const useModalVisible: useModalVisibleProps = (init, toogleModalStore) => {
    const [modalVisible, setModalVisible] = useState(init);

    const dispatch = useAppDispatch();

    const toogleModal = () => {
        setModalVisible((prevState) => !prevState);
        return modalVisible;
    };

    const toogleModalWithStore = () => {
        toogleModal();
        if (toogleModalStore) dispatch(toogleModalStore());
    };

    return {
        modalVisible,
        toogleModal,
        setModalVisible,
        toogleModalWithStore,
    };
};

export default useModalVisible;
