import { useNavigate } from 'react-router-dom';

import { useForm } from 'react-hook-form';

import { useMutation } from 'react-query';

import { signUp } from './services/api';

import SignUp from './signUp';
import HeaderContainer from './HeaderContainer';

import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

export default function SignUpContainer() {
  const schema = yup.object().shape({
    passwordCheck: yup
      .string()
      .oneOf([yup.ref('password'), '비밀번호가 일치하지않습니다'])
      .required(),
  });

  const { register, formState: { errors }, handleSubmit, watch, setError } = useForm({
    resolver: yupResolver(schema),
  });
  const navigate = useNavigate();

  const signUpMutate = async ({
    email,
    password,
    name,
  }: { email: any, password: string, name: any }) => {
    const signUpResult = await signUp({ email, password, name });
    return signUpResult;
  };

  const { error, mutate } = useMutation('signup', signUpMutate, {
    onSuccess: async () => {
      console.log('회원가입 성공');
      alert('회원가입 되었습니다');
      navigate('/login', { replace: true });
    },
    onError: async (e) => {
      console.error(e);
    },
  });

  return (
    <>
      <HeaderContainer/>
      <SignUp
        register={register}
        errors={errors}
        handleSubmit={handleSubmit}
        error={error}
        signUp={mutate}
        watch={watch}
        setError={setError}
      />
    </>
  );
}
