import NotAuthentificatedArea from '../NotAuthentificatedArea/NotAuthentificatedArea';
import AccountPopover from '../AccountPopover/AccountPopover';

const AccountArea = () => {
    const isAuth = false;

    return isAuth ? <AccountPopover /> : <NotAuthentificatedArea />;
};

export default AccountArea;
