import { Modal } from 'antd';
import { ModalProps } from '../../types/ModalProps';

const SignUpModal = ({ visible, toogleModal }: ModalProps) => {
    const handleOk = () => {
        toogleModal();
    };

    return (
        <Modal
            centered
            title="Sign Up"
            visible={visible}
            onOk={handleOk}
            onCancel={() => toogleModal()}
        >
            gfds123123
        </Modal>
    );
};

export default SignUpModal;
