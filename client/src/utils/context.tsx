import {ReactNode, Reducer, createContext, useReducer} from 'react'
import { ActionTypes, Actions, Context, State } from './types'

export const context = createContext({} as Context)

const reducer: Reducer<State, Actions> = (state: State, action: Actions): State => {
  switch (action.type) {
    case ActionTypes.SET_ACCESS_TOKEN:
      return {...state, accessToken: action.payload}
    case ActionTypes.SET_IS_AUTH:
      return {...state, isAuth: action.payload}
    default:
      return state
  }
}

const ContextProvider = ({children}: {children: ReactNode}) => {
  const [state, dispatch] = useReducer(reducer, {isAuth: false, accessToken: ''})

  return (
    <context.Provider value={{state, dispatch}}>
      {children}
    </context.Provider>
  )
}

export default ContextProvider