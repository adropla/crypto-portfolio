export type ModalProps = {
    visible: boolean;
    toogleLoginModal: () => void;
    toogleSignUpModal: () => void;
    toogleForgotModal: () => void;
};

export type LoginModalProps = ModalProps;

export type SignupModalProps = Omit<ModalProps, 'toogleForgotModal'>;

export type ForgotPasswordModalProps = Omit<ModalProps, 'toogleSignUpModal'>;
