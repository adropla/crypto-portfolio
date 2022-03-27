import { useState } from 'react';

const useModalVisible = (init: boolean) => {
    const [modalVisible, setModalVisible] = useState(init);

    const toogleModal = () => {
        setModalVisible((prevState) => !prevState);
        return modalVisible;
    };

    return {
        modalVisible,
        toogleModal,
        setModalVisible,
    };
};

export default useModalVisible;
