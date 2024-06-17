import { Box, Button, Input } from '@chakra-ui/react'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { instance } from '../utils/instance'
import { IComment, IVideo, ReviewTypes } from '../utils/types'
import { context } from '../utils/context'
import Comment from '../components/Comment'

const VideoPage = () => {
  const {id} = useParams()
  const [video, setVideo] = useState<IVideo>({
    comments: [] as IComment[]
  } as IVideo)
  const [comment, setComment] = useState<string>('')
  const {accessToken} = useContext(context).state

  useEffect(() => {
    instance.get<IVideo>(`/videos/getOne/${id}`)
    .then(({data}) => {
      setVideo(data)
    })
    .catch((error) => console.log(error))
  }, [])

  function addReview(reviewType: ReviewTypes) {
    instance.post('/videos/addReview', {videoId: Number(id), reviewType}, {
      headers: {
        'Authorization': `Bearer ${accessToken}`
      }
    })
    .catch((error) => console.log(error))
  }

  function createComment() {
    instance.post('/comments/create', {content: comment, videoId: Number(id)}, {
      headers: {
        'Authorization': `Bearer ${accessToken}`
      }
    })
  }

  return (
    <Box className='video-page'>
      <video 
        style={{margin: '30px auto'}} 
        src={video.videoUrl}
        controls 
        autoPlay
      ></video>
      <h1 id='video-title'>{video.title}</h1>
      <Box className='reviews-box'>
        <span 
          onClick={() => addReview(ReviewTypes.LIKE)} 
          className="material-symbols-outlined"
        >thumb_up</span>
        <h1>{video.likes}</h1>
        <span 
          onClick={() => addReview(ReviewTypes.DISLIKE)} 
          className="material-symbols-outlined"
        >thumb_down</span>
        <h1>{video.dislikes}</h1>
      </Box>
      <Box className='video-page-form'>
        <Input 
          placeholder='Enter a comment...'
          value={comment}
          onChange={e => setComment(e.target.value)}
        />
        <Button onClick={createComment}>Send</Button>
      </Box>
      <Box className='comment-wrapper'>
        {video.comments.map((comment: IComment) => {
          return <Comment comment={comment} key={comment.id} />
        })}
      </Box>
    </Box>
  )
}

export default VideoPage