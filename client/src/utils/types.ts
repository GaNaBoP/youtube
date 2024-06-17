import { Dispatch, Reducer, ReducerAction } from "react"

export interface AuthResponse {
  accessToken: string
  refreshToken: string
}

export interface IVideo {
  id: number
  title: string
  description: string
  videoUrl: string
  previewUrl: string
  likes: number
  dislikes: number
  comments: IComment[]
}

export interface IComment {
  id: number
  content: string
}

export interface IUser {
  id: number
  email: string
  login: string
  isActivated: boolean
  videos: IVideo[]
}

export enum ReviewTypes {
  LIKE = 0, 
  DISLIKE = 1
}

export interface State {
  accessToken: string
  isAuth: boolean
}

export enum ActionTypes {
  SET_ACCESS_TOKEN,
  SET_IS_AUTH,
}

interface SetAccessTokenAction {
  type: ActionTypes.SET_ACCESS_TOKEN
  payload: string
}

interface SetIsAuthAction {
  type: ActionTypes.SET_IS_AUTH
  payload: boolean
}

export interface Context {
  state: State
  dispatch: Dispatch<ReducerAction<Reducer<State, Actions>>>
}

export type Actions = SetAccessTokenAction | SetIsAuthAction