import NotAuthentificatedArea from '../NotAuthentificatedArea/NotAuthentificatedArea';
import AccountPopover from '../AccountPopover/AccountPopover';
import { selectToken } from '../../redux/selectors/authSelectors';
import { useAppSelector } from '../../hooks/redux';

const AccountArea = () => {
    const isAuth = useAppSelector(selectToken);

    return isAuth ? <AccountPopover /> : <NotAuthentificatedArea />;
};

export default AccountArea;
