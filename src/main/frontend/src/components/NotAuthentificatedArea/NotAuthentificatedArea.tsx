import { Button } from 'antd';
import useModalVisible from '../../hooks/useModalVisible';
import LoginModal from '../LoginModal/LoginModal';

import styles from './NotAuthentificatedArea.module.scss';

const NotAuthentificatedArea = () => {
    const loginModalVisible = useModalVisible(false);
    const signupModalVisible = useModalVisible(false);

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
            <LoginModal />
        </div>
    );
};

export default NotAuthentificatedArea;
