import { Button } from 'antd';
import useModalVisible from '../../hooks/useModalVisible';
import ForgotPasswordModal from '../ForgotPasswordModal/ForgotPasswordModal';
import LoginModal from '../LoginModal/LoginModal';
import SignUpModal from '../SignUpModal/SignUpModal';

import styles from './NotAuthentificatedArea.module.scss';

const NotAuthentificatedArea = () => {
    const loginModalVisible = useModalVisible(false);
    const signupModalVisible = useModalVisible(false);
    const forgotPasswordModalVisible = useModalVisible(false);

    const handelLogin = () => {
        loginModalVisible.toogleModal();
    };

    const handleSignup = () => {
        signupModalVisible.toogleModal();
    };

    return (
        <div className={styles.wrapper}>
            <Button onClick={handelLogin} className={styles.loginBtn}>
                Log In
            </Button>
            <Button
                onClick={handleSignup}
                className={styles.signupBtn}
                type="primary"
            >
                Sign Up
            </Button>
            <LoginModal
                visible={loginModalVisible.modalVisible}
                toogleLoginModal={loginModalVisible.toogleModal}
                toogleSignUpModal={signupModalVisible.toogleModal}
                toogleForgotModal={forgotPasswordModalVisible.toogleModal}
            />
            <SignUpModal
                visible={signupModalVisible.modalVisible}
                toogleLoginModal={loginModalVisible.toogleModal}
                toogleSignUpModal={signupModalVisible.toogleModal}
            />
            <ForgotPasswordModal
                visible={forgotPasswordModalVisible.modalVisible}
                toogleLoginModal={loginModalVisible.toogleModal}
                toogleForgotModal={forgotPasswordModalVisible.toogleModal}
            />
        </div>
    );
};

export default NotAuthentificatedArea;
