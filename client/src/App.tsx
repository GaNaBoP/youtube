import { Navigate, Route, Routes, useNavigate } from "react-router-dom"
import { publicRoutes, privateRoutes } from "./utils/routes"
import { useContext, useEffect } from "react"
import { instance } from "./utils/instance"
import { ActionTypes, AuthResponse } from "./utils/types"
import { context } from "./utils/context"

const App = () => {
  const {state: {isAuth}, dispatch} = useContext(context)
  const navigate = useNavigate()

  useEffect(() => {
    const refreshTokens = () => {
      const refreshToken = localStorage.getItem('refreshToken')
      if (refreshToken) {
        (async () => {
          try {
            const { data } = await instance.get<AuthResponse>('/users/refresh', {
              headers: {
                'Authorization': `Bearer ${refreshToken}`
              }
            })
            dispatch({type: ActionTypes.SET_ACCESS_TOKEN, payload: data.accessToken})
            localStorage.setItem('refreshToken', data.refreshToken)
            dispatch({type: ActionTypes.SET_IS_AUTH, payload: true})
          } catch {
            navigate('/auth')
          }
        })()
      } else {
        navigate('/auth')
      }
    }

    refreshTokens()
  }, [])

  return (
    <Routes>
      {publicRoutes.map((route) => {
        return <Route key={route.path} path={route.path} Component={route.component} />
      })}
      {isAuth && privateRoutes.map((route) => {
        return <Route key={route.path} path={route.path} Component={route.component} />
      })}
      <Route path="*" element={<Navigate to='/home/0' />} />
    </Routes>
  )
}

export default App