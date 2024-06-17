import { Box, Button, Input } from "@chakra-ui/react"
import { useContext, useState } from "react"
import { instance } from "../utils/instance"
import { context } from "../utils/context"

const CreatePage = () => {
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [video, setVideo] = useState<File | any>('')
  const [preview, setPreview] = useState<File | any>('')
  const {accessToken} = useContext(context).state

  const createVideo = () => {
    const formData = new FormData()
    formData.append('title', title)
    formData.append('description', description)
    formData.append('video', video)
    formData.append('preview', preview)
    
    instance.post('/videos/create', formData, {
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'multipart/form-data'
      }
    })
    .catch((error) => console.log(error))
  }

  return (
    <Box className="wrapper create-page">
      <h1>Post your video</h1>
      <Input 
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Enter the title..."
      />
      <Input 
        value={description}
        onChange={e => setDescription(e.target.value)}
        placeholder="Enter the description..."
      />
      <Box className='file-buttons-group'>
        <label className="file-button" htmlFor="video">Select video</label>
        <input onChange={e => {
            if (e.target.files) { setVideo(e.target.files[0]) } 
        }} style={{display: 'none'}} id="video" type="file" />
        <label className="file-button" htmlFor="preview">Select preview</label>
        <input onChange={e => {
            if (e.target.files) { setPreview(e.target.files[0]) }         
          }} style={{display: 'none'}} id="preview" type="file" />
      </Box>
      <Button onClick={createVideo} className="create-video-button">Create</Button>
    </Box>
  )
}

export default CreatePage