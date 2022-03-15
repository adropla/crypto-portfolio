import { UserOutlined } from '@ant-design/icons';
import { Button, Popover, Typography, Avatar } from 'antd';
import { Link } from 'react-router-dom';

import ROUTES from '../../constants/routes';

import styles from './AccountPopover.module.scss';

const { Text } = Typography;

const AccountPopoverContent = () => {
    const username = 'userfdsagfsadfsdfadsfsadfsdafsdfsda';

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
            <Button className={styles.btn}>Log out</Button>
        </>
    );
};

const AccountPopover = () => {
    const isAuth = false;

    return (
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
};

export default AccountPopover;
