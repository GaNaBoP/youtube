import { Button, ButtonGroup, Input, Box, FormControl, FormLabel } from '@chakra-ui/react'
import { useState, useContext, ChangeEvent } from 'react'
import { useNavigate } from 'react-router-dom'
import { instance } from '../utils/instance'
import { ActionTypes, AuthResponse } from '../utils/types'
import { context } from '../utils/context'

const AuthPage = () => {
  const [form, setForm] = useState({email: '', login: '', password: ''})
  const [confirmationWindow, setConfirmationWindow] = useState(false)
  const [confirmationToken, setConfirmationToken] = useState('')
  const navigate = useNavigate()
  const {state: {accessToken}, dispatch} = useContext(context)

  function auth(path: string) {
    instance.post<AuthResponse>(`/users/${path}`, form)
    .then(({data}) => {
      localStorage.setItem('refreshToken', data.refreshToken)
      dispatch({type: ActionTypes.SET_ACCESS_TOKEN, payload: data.accessToken})
      if (path === 'login') 
        navigate('/home/0')
      if (path === 'register') 
        setConfirmationWindow(true)
    })
    .catch((error) => console.log(error))
    console.log(form)
  }

  function formHandler(e: ChangeEvent<HTMLInputElement>) {
    setForm(prev => ({...prev, [e.target.name]: e.target.value}))
  }

  function activateAccount() {
    instance.post(`/users/activate`, {confirmationToken}, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    })
    .then(() => {
      navigate('/home/0')
    })
    .catch((error) => console.log(error))
  }

  return (
    <Box className="wrapper auth-page">
      {confirmationWindow && <Box className='confirmation-alert'>
        <h1>Enter the token that will be sent to your email</h1>
        <FormControl sx={{width: '50%'}}>
          <FormLabel>Your Token</FormLabel>
          <Input 
            size='lg'
            value={confirmationToken} 
            onChange={e => setConfirmationToken(e.target.value)} 
          />
        </FormControl>
        <Button variant='contained' onClick={activateAccount}>Confirm</Button>
      </Box>}
      <h1>My Youtube Clone</h1>
      <Input 
        size='lg'
        placeholder='Email...'
        name='email'
        onChange={formHandler}
      />
      <Input 
        size='lg'
        placeholder='Login...'
        name='login'
        onChange={formHandler}
      />
      <Input 
        size='lg'
        placeholder='Password...'
        name='password'
        onChange={formHandler}
        type='password'
      />
      <ButtonGroup className='auth-buttons'>
        <Button colorScheme='cyan' onClick={() => auth('register')}>Register</Button>
        <Button colorScheme='cyan' onClick={() => auth('login')}>Login</Button>
      </ButtonGroup>
    </Box>
  )
}

export default AuthPage