import { Button } from 'antd';
import { useEffect } from 'react';
import { useAppSelector } from '../../hooks/redux';
import useModalVisible from '../../hooks/useModalVisible';
import { toogleLoginModalVisible } from '../../redux/reducers/loginModalSlice';
import selectLoginModalVisible from '../../redux/selectors/loginModalSelectors';
import ForgotPasswordModal from '../ForgotPasswordModal/ForgotPasswordModal';
import LoginModal from '../LoginModal/LoginModal';
import SignUpModal from '../SignUpModal/SignUpModal';

import styles from './NotAuthentificatedArea.module.scss';

const NotAuthentificatedArea = () => {
    const loginModalVisibleFromStore = useAppSelector(selectLoginModalVisible);
    const loginModalVisible = useModalVisible(false, toogleLoginModalVisible);
    const signupModalVisible = useModalVisible(false);
    const forgotPasswordModalVisible = useModalVisible(false);

    const handelLogin = () => {
        loginModalVisible.toogleModalWithStore();
    };

    const handleSignup = () => {
        signupModalVisible.toogleModal();
    };

    useEffect(() => {
        loginModalVisible.setModalVisible(loginModalVisibleFromStore);
    }, [loginModalVisibleFromStore]);

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
                toogleLoginModal={loginModalVisible.toogleModalWithStore}
                toogleSignUpModal={signupModalVisible.toogleModal}
                toogleForgotModal={forgotPasswordModalVisible.toogleModal}
            />
            <SignUpModal
                visible={signupModalVisible.modalVisible}
                toogleLoginModal={loginModalVisible.toogleModalWithStore}
                toogleSignUpModal={signupModalVisible.toogleModal}
            />
            <ForgotPasswordModal
                visible={forgotPasswordModalVisible.modalVisible}
                toogleLoginModal={loginModalVisible.toogleModalWithStore}
                toogleForgotModal={forgotPasswordModalVisible.toogleModal}
            />
        </div>
    );
};

export default NotAuthentificatedArea;
