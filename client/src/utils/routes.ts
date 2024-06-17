import react from 'react'
import AuthPage from '../pages/AuthPage'
import HomePage from '../pages/HomePage'
import CreatePage from '../pages/CreatePage'
import VideoPage from '../pages/VideoPage'
import ProfilePage from '../pages/ProfilePage'

interface IRoute {
  path: string,
  component: react.FC
}

export const publicRoutes: IRoute[] = [
  {path: '/auth', component: AuthPage},
  {path: '/home/:id', component: HomePage},
  {path: '/video/:id', component: VideoPage},
]

export const privateRoutes: IRoute[] = [
  {path: '/create', component: CreatePage},
  {path: '/profile', component: ProfilePage},
]