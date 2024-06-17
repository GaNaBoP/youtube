import { Box, Button, ButtonGroup, Input } from "@chakra-ui/react"
import { useContext, useEffect, useState } from "react"
import { instance } from "../utils/instance"
import { IUser, IVideo } from "../utils/types"
import { context } from "../utils/context"

const ProfilePage = () => {
  const {accessToken} = useContext(context).state
  const [user, setUser] = useState<IUser>({videos: [] as IVideo[]} as IUser)
  const [loading, setLoading] = useState(true)
  const [editedVideo, setEditedVideo] = useState<IVideo>({} as IVideo)
  const [isEdit, setIsEdit] = useState(false)

  useEffect(() => {
    const getMe = () => {
      instance.get<IUser>('/users/getMe', {
        headers: {
          'Authorization': `Bearer ${accessToken}`
        }
      })
      .then(({data}) => {
        setUser(data)
        setLoading(false)
      })
      .catch(() => setLoading(false))
    }

    getMe()
  }, [])

  function updateVideo() {
    instance.put(`/videos/`, {
      videoId: editedVideo.id,
      newTitle: editedVideo.title,
      newDescription: editedVideo.description
    }, {
      headers: {
        'Authorization': `Bearer ${accessToken}`
      }
    })
    .catch(() => setLoading(false))
  }

  function deleteVideo() {
    instance.delete(`/videos/${editedVideo.id}`, {
      headers: {
        'Authorization': `Bearer ${accessToken}`
      }
    })
    .catch((error) => console.log(error))
  }

  return (
    <Box className='profile-page'>
      <Box className='my-videos'>
      {!loading && user.videos.map((video) => 
        <Box 
          onClick={() => {
            setEditedVideo(video)
            setIsEdit(true)
          }} 
          className='my-video' 
          key={video.id}
        >
          <img src={video.previewUrl} alt="" />
          <h1>{video.title}</h1>
        </Box>
      )}
      </Box>
      {isEdit && <Box className='video-editor'>
        <Input 
          placeholder="Enter a new title..."
          value={editedVideo.title}
          onChange={e => setEditedVideo((prev) => ({...prev, title: e.target.value}))}
        />
        <Input 
          placeholder="Enter a new description..."
          value={editedVideo.description}
          onChange={e => setEditedVideo((prev) => ({...prev, description: e.target.value}))}
        />
        <ButtonGroup gap='2'>
          <Button
            colorScheme='yellow' size='lg'
            onClick={updateVideo}
          >Update</Button>
          <Button
            colorScheme='red' size='lg'
            onClick={deleteVideo}
          >Delete</Button>
        </ButtonGroup>
      </Box>}
    </Box>
  )
}

export default ProfilePage