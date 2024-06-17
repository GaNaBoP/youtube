import { Center, Spinner } from "@chakra-ui/react"

const Loading = () => {
  return (
    <Center
        width='100vw'
        height='100vh'
      >
        <Spinner
          thickness='4px'
          speed='0.65s'
          emptyColor='gray.200'
          color='blue.500'
          size='xl'
        />
      </Center>
  )
}

export default Loading