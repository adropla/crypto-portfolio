/* eslint-disable react/prop-types */
import { Route, Redirect, RouteProps } from 'react-router-dom';

import ROUTES from '../../constants/routes';
import { useAppDispatch } from '../../hooks/redux';
import { toogleLoginModalVisible } from '../../redux/reducers/loginModalSlice';

export interface IAuthRoute extends RouteProps {
    component: React.FC;
    isAuthenticated: boolean;
    [x: string]: any;
}

const AuthRoute: React.FC<IAuthRoute> = ({
    component: Component,
    isAuthenticated,
    ...restProps
}) => {
    const dispatch = useAppDispatch();
    if (!isAuthenticated) {
        dispatch(toogleLoginModalVisible());
        return <Redirect to={ROUTES.main} />;
    }

    // eslint-disable-next-line react/jsx-props-no-spreading
    return <Route {...restProps} render={() => <Component {...restProps} />} />;
};

export { AuthRoute };
