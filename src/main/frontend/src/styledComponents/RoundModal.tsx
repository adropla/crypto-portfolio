import { Modal } from 'antd';
import styled from 'styled-components';

const RoundModal = styled(Modal)<{ radius: string }>`
    .ant-modal-content,
    .ant-modal-header {
        border-radius: ${(props) => props.radius};
    }
`;

export default RoundModal;
