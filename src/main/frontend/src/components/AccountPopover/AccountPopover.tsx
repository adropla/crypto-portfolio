import { LogoutOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Popover, Typography, Avatar, message } from 'antd';
import { Link } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from '../../hooks/redux';
import { clearCredentials } from '../../redux/reducers/authSlice';
import { selectUsername } from '../../redux/selectors/authSelectors';

import ROUTES from '../../constants/routes';
import styles from './AccountPopover.module.scss';
import { useLogoutMutation } from '../../services/serverApi';

const { Text } = Typography;

const AccountPopoverContent = () => {
    const dispatch = useAppDispatch();
    const username = useAppSelector(selectUsername);

    const [logoutTrigger] = useLogoutMutation();


    const logout = () => {
        logoutTrigger('');
        dispatch(clearCredentials());
        message.success({
            content: 'You are logged out',
            icon: <LogoutOutlined />,
        });
    };

    return (
        <>
            <div className={styles.popoverWrapper}>
                <Link to={ROUTES.settings}>
                    <Avatar size={64} icon={<UserOutlined />} />
                </Link>
                <div className={styles.rightSide}>
                    <Link to={ROUTES.settings}>
                        <Text className={styles.username}>{username}</Text>
                    </Link>
                    <Link to={ROUTES.settings}>
                        <Text className={styles.view}>View profile</Text>
                    </Link>
                </div>
            </div>
            <Button className={styles.btn} onClick={logout}>
                Log out
            </Button>
        </>
    );
};

const AccountPopover = () => (
    <Popover content={AccountPopoverContent}>
        <UserOutlined
            className={styles.accountIcon}
            style={{
                fontSize: '30px',
                marginRight: '20px',
                color: 'white',
            }}
        />
    </Popover>
);

export default AccountPopover;
