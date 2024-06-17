import { Box } from "@chakra-ui/react"
import { IComment } from "../utils/types"

interface Props {
  comment: IComment
}

const Comment = ({comment}: Props) => {
  return (
    <Box className='comment'>
      <h1>{comment.content}</h1>
    </Box>
  )
}

export default Comment