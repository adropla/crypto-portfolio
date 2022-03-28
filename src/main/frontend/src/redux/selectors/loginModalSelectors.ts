import { RootState } from '../store';

const selectLoginModalVisible = (state: RootState) =>
    state.loginModalSlice.visible;
export default selectLoginModalVisible;
